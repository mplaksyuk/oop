import React, { useState, useEffect, useContext } from 'react';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { useForm } from "react-hook-form";
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { MultiInputDateRangeField } from '@mui/x-date-pickers-pro/MultiInputDateRangeField';
import { LineChart } from '@mui/x-charts/LineChart';
import { BarChart } from '@mui/x-charts/BarChart';
import { PieChart } from '@mui/x-charts/PieChart';
import Box from '@mui/material/Box';
import Stack from '@mui/material/Stack';
import Typography from '@mui/material/Typography';

import PopularityProductTable from '../../components/Reports/PopularityProductTable';
import SpendIngredientTable from '../../components/Reports/SpendIngredientTable'
import UserPopularityTable from '../../components/Reports/UserPopularityTable'
import ProductPercentageTable from '../../components/Reports/ProductPercentageTable'
import ProductRevenueTable from '../../components/Reports/ProductRevenueTable';


import Header from '../../components/Header/Header';
import Axios from '../../Axios';

import './Reports.css'

import { UserContext } from '../../App';
import { renderMatches } from 'react-router-dom';

export default function Reports() {
    const [reportUrlPart, setReportUrlPart] = useState('/product-popularity');
    const [reportType, setReportType] = useState("productPopularity");
    const [downloadHref, setDownloadHref] = useState(null);

    const [dateRange, setDateRange] = useState([]);
    const [lastReport, setLastReport] = useState([]);
    const [reportData, setReportData] = useState({});

    const [products, setProducts] = useState([]);
    const [ingredients, setIngredients] = useState([]);

    const [Graph, setGraph] = useState();
    const [xAsixData, setXAxisData] = useState([]);
    const [yAsixDataName, setYAxisDataName] = useState('');

    useEffect(() => {
        Axios.get('/products').then(res => { setProducts(res.data.map(product => product.name)); });
        Axios.get('/ingredients').then(res => {setIngredients(res.data.map(ingredient => ingredient.name));});
    }, [])

    const doGet = () => {
        const [f, t] = dateRange;
        const from = `${f?.$y}-${f?.$M + 1}-${f?.$D}`;
        const thru = `${t?.$y}-${t?.$M + 1}-${t?.$D}`;
        Axios.get(`/report${reportUrlPart}?from=${from}T00:00:00&thru=${thru}T00:00:00`).then(res => {
            const report = res.data;
            setLastReport(report);
            setReportData({ ...reportData, [`${from} : ${thru}`]: report });
        });
    }

    useEffect(() => {
        const [f, t] = dateRange;
        if (f && t && f.$d < t.$d) {
            doGet();
        }
        setLastReport([]);
        setReportData({});
    }, [reportUrlPart])
    
    useEffect(() => {
        const [f, t] = dateRange;
        const from = `${f?.$y}-${f?.$M + 1}-${f?.$D}`;
        const thru = `${t?.$y}-${t?.$M + 1}-${t?.$D}`;
        if (f && t && f.$d < t.$d) {
            Axios.request({
                url: `/report/report-excel?from=${from}T00:00:00&thru=${thru}T00:00:00`,
                method: 'GET',
                responseType: 'blob',
            }).then(res => {
                const report = res.data;
                const href = URL.createObjectURL(report);

                setDownloadHref(href);
            });
        }
     }, dateRange)


    const handleSelect = (urlPart, type) => {
        setReportUrlPart(urlPart);
        setReportType(type);
        setLastReport([]);
        setReportData({});
    }

    const submit = () => {
        const [f, t] = dateRange;
        if (f && t && f.$d < t.$d) {
            doGet();
        }
        else {
            alert("From date must be less then Thru date");
        }
    }

    useEffect(() => {
        if (['productRevenue', 'ingredientSpent'].includes(reportType)) {
            setGraph(BarChart);
        }
        else setGraph(null);

        if (['productRevenue'].includes(reportType)) {
            setXAxisData(products);
        }
        else if (['ingredientSpent'].includes(reportType)) {
            setXAxisData(ingredients);
        }
        else setXAxisData(null);

        switch (reportType) {
            case 'productRevenue': setYAxisDataName('finalProfit'); break;
            case 'ingredientSpent': setYAxisDataName('totalPrice'); break;
            default: setYAxisDataName('');
        }

        console.log(Graph);
    }, [reportType])      
      const palette = [
        'red', 'blue', 'green', 'orange', 'purple', 'yellow', 'cyan', 'pink', 'brown', 'teal',
        'lime', 'indigo', 'maroon', 'navy', 'olive', 'orchid', 'plum', 'gold', 'silver', 'peru',
        'salmon', 'seagreen', 'sienna', 'skyblue', 'slategray', 'springgreen', 'tan', 'thistle', 'tomato', 'violet'
      ];
      
      console.log(palette);
      
    
    return (
        <>
            <Header />
            <Container>
                <Form>
                    <div className="mb-3">
                        {[
                            ['Популярність Продуктів', '/product-popularity', 'productPopularity'],
                            ['Кількість Витрачених Інгредієнтів', '/ingredient-spend', 'ingredientSpent'],
                            ['Популярність Користувачів', '/user-top', 'userPopularity'],
                            ['Середній вміст продукту в чек', '/product-percentage', 'productPercentage'],
                            ['Прибуток Кожного Продукту', '/product-revenue', 'productRevenue']
                        ].map(([label, urlPart, value], index) =>
                            (<Form.Check inline label={label} name="reportType" type="radio" checked={value == reportType} onChange={() => handleSelect(urlPart, value) } />)
                        )}
                    </div>
                    <div className="mb-3 d-flex gap-2 report-options">
                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                            <MultiInputDateRangeField
                                slotProps={{
                                    textField: ({ position }) => ({
                                        label: position === 'start' ? 'From' : 'Thru',
                                    }),
                                }}
                                onChange={setDateRange}
                            />
                        </LocalizationProvider>
                        
                        <Button variant="success" onClick={(e) => { e.preventDefault(); submit(); }}>Згенерувати</Button>
                        {downloadHref && (
                            <Button href={downloadHref} download="report.xlsx" variant="success" className="download-report">Download</Button>
                        )}
                    </div>

                    {(reportType == 'productPopularity' && lastReport.length) ? (<PopularityProductTable data={ lastReport } />) : <></>}
                    {(reportType == 'ingredientSpent'   && lastReport.length) ? (<SpendIngredientTable   data={ lastReport } />) : <></>}
                    {(reportType == 'userPopularity'    && lastReport.length) ? (<UserPopularityTable    data={ lastReport } />) : <></>}
                    {(reportType == 'productPercentage' && lastReport.length) ? (<ProductPercentageTable data={ lastReport } />) : <></>}
                    {(reportType == 'productRevenue'    && lastReport.length) ? (<ProductRevenueTable    data={ lastReport } />) : <></>}
                    {(Graph) ?
                        <>
                            <BarChart
                                width={1080}
                                height={720}
                                margin={{ bottom: 300 }}
                                series={
                                    Object.entries(reportData).map(([label, data]) => {
                                        const x = xAsixData.reduce((acc, title) => {
                                            acc[title] = null
                                            return acc;
                                        }, {});
                                        data.map(e => x[e.name] = e[yAsixDataName])
                                        return { data: Object.values(x), label };
                                    })
                                }
                                xAxis={[{ tickLabelStyle: { angle: 90, textAnchor: 'start' }, scaleType: Graph === BarChart ? 'band' : 'point', data: xAsixData }]}
                            />
                        </>
                        : <></>}
                </Form>
            </Container>
        </>    
    )
}
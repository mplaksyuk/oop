import React, { useState, useEffect, useContext } from 'react';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import { useForm } from "react-hook-form";

import PopularityProductTable from '../../components/Reports/PopularityProductTable';

import Header from '../../components/Header/Header';
import Axios from '../../Axios';

import { UserContext } from '../../App';

export default function Reports() {

    const { register, handleSubmit } = useForm();

    const [reportUrlPart, setReportUrlPart] = useState('/product/popularity')
    const [reportType, setReportType] = useState("productPopularity");

    const [reportData, setReportData] = useState([]);

    const handleSelect = (urlPart, type) => {
        setReportUrlPart(urlPart);
        setReportType(type);
    }

    useEffect(() => {
        const from = '2021-11-22T16:41:33.857+00:00';
        const thru = '2024-11-22T16:41:33.857+00:00';
        Axios.get(`/report/?from=${from}&thru=${thru}`).then(res => {
            const report = res.data;
            setReportData(report);
        })
    }, [reportUrlPart]);

    return (
        <>
            <Header />
            <Container>
                <Form>
                    <div className="mb-3">
                        {[
                            ['Популярність Продуктів', '/product/popularity', 'productPopularity'],
                            ['', ''],
                            ['', ''],
                            ['', '']
                        ].map(([label, urlPart, value], index) =>
                            (<Form.Check inline label={label} name="reportType" type="radio" checked={value == reportType} onChange={() => handleSelect(urlPart, value) } />)
                        )}
                    </div>
                    {['productPopularity'].includes(reportType) &&
                    (<div className="mb-3 d-flex gap-2 w-25">
                        <Form.Control inline type="date" placeholder="From" {...register('from', { required: true })} />
                        <Form.Control inline type="date" placeholder="Thru" { ...register('thru', { required : true }) } />
                    </div>)
                    }
                    <PopularityProductTable data={ reportData } />
                </Form>
            </Container>
        </>    
    )
}
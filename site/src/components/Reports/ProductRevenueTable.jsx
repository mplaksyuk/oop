import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

export default function ProductRevenueTable({ data }) {
    return (
        <TableContainer  className="report-table" component={Paper}>
            <Table aria-label="collapsible table">
                <TableHead>
                    <TableRow>
                        <TableCell>Назва Продукту</TableCell>
                        <TableCell>Загальна Кількість</TableCell>
                        <TableCell>Загальна Ціна Продукту (грн.)</TableCell>
                        <TableCell>Загальна Собівартість Продукту (грн.)</TableCell>
                        <TableCell>Загальний Прибуток Продукту (грн.)</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                { data.map((d) => (
                    <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
                        <TableCell>{d?.name }</TableCell>
                        <TableCell>{d?.countOfProducts }</TableCell>
                        <TableCell>{d?.productsRevenue + ' грн.'}</TableCell>
                        <TableCell>{d?.primeCost + ' грн.'}</TableCell>
                        <TableCell>{d?.finalProfit + ' грн.'}</TableCell>
                    </TableRow>
                )) }
                </TableBody>
            </Table>
        </TableContainer>
    )
}
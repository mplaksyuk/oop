import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

export default function ProductPercentageTable({ data }) {
    return (
        <TableContainer  className="report-table" component={Paper}>
            <Table aria-label="collapsible table">
                <TableHead>
                    <TableRow>
                        <TableCell>Назва Продукту</TableCell>
                        <TableCell>Прибуток (%)</TableCell>
                        <TableCell>Загальна сума чеків (грн.)</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                { data.map((d) => (
                    <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
                        <TableCell>{d?.name}</TableCell>
                        <TableCell>{d?.percent?.toFixed(1) + '%'}</TableCell>
                        <TableCell>{d?.sumTotal + ' грн.'}</TableCell>
                    </TableRow>
                )) }
                </TableBody>
            </Table>
        </TableContainer>
    )
}
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

export default function PopularityProductTable({ data }) {
    return (
        <TableContainer component={Paper}>
            <Table aria-label="collapsible table">
                <TableHead>
                    <TableRow>
                        <TableCell>Назва Продукту</TableCell>
                        <TableCell>Кількість</TableCell>
                        <TableCell>Загальна ціна&nbsp;(грн)</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                { data.map((d) => (
                    <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
                        <TableCell>{d?.name}</TableCell>
                        <TableCell>{d?.quantity}</TableCell>
                        <TableCell>{d?.totalPrice + ' грн'}</TableCell>
                    </TableRow>
                )) }
                </TableBody>
            </Table>
        </TableContainer>
    )
}
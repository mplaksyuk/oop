import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

export default function SpendIngredientTable({ data }) {
    const units = {
        'MILLIGRAM': 'мг.',
        'GRAM': 'г.',
        'MILLILITER': ' мл.',
        'QUANTITY': 'шт.'
    }
    return (
        <TableContainer  className="report-table" component={Paper}>
            <Table aria-label="collapsible table">
                <TableHead>
                    <TableRow>
                        <TableCell>Назва Інгредієнту</TableCell>
                        <TableCell>Кількість</TableCell>
                        <TableCell>Загальна Вага</TableCell>
                        <TableCell>Загальна Ціна (грн.)</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                { data.map((d) => (
                    <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
                        <TableCell>{d?.name}</TableCell>
                        <TableCell>{d?.quantity}</TableCell>
                        <TableCell>{d?.volume + ' ' + units[d?.unit]}</TableCell>
                        <TableCell>{d?.totalPrice + ' грн.'}</TableCell>
                    </TableRow>
                )) }
                </TableBody>
            </Table>
        </TableContainer>
    )
}
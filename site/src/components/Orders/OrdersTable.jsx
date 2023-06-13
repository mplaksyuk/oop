import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';

import { SlArrowUp, SlArrowDown } from 'react-icons/sl'
import Button from 'react-bootstrap/Button';
import Axios from '../../Axios';

function Row({ row, user }) {
  const [open, setOpen] = useState(false);

  const [ order, setOrder ] = useState(null);

  const [ totalPrice, setTotalPrice ] = useState(0);
  const [ totalWeight, setTotalWeight ] = useState(0);

  const groupProducts = (products) => {
    return products.reduce((acc, product) => {
      const foundProduct = acc.find((i) => i.id === product.id);
      if (foundProduct) {
        foundProduct.quantity++;
      } else {
        acc.push({...product, quantity : 1});
      }
      return acc;
    }, [])
  }

  const dateFormat = (mills) => {
    const date = new Date(mills);
    
    const day = date.getDate().toString().padStart(2, "0");
    const month = (date.getMonth() + 1).toString().padStart(2, "0");
    const year = date.getFullYear().toString();
    const hours = date.getHours().toString().padStart(2, "0");
    const minutes = date.getMinutes().toString().padStart(2, "0");
    const seconds = date.getSeconds().toString().padStart(2, "0");

    const formattedDate = `${day}.${month}.${year} ${hours}:${minutes}:${seconds}`;

    return formattedDate;
  }

  const handleConfirmOrder = () => {
    Axios.post(`/orders/${order.id}/confirm`).then((order) => {
      order.data.products = groupProducts(order.data.products);
      setOrder(order.data);
    })
  }

  const handlePaidOrder = () => {
    Axios.post(`/orders/${order.id}/paid`).then((order) => {
      order.data.products = groupProducts(order.data.products);
      setOrder(order.data);
    })
  }

  useEffect(() => {
    const products = groupProducts(row.products)

    row.products = products;

    setOrder(row);

    setTotalWeight(products.reduce((acc, product) => { return acc + product.weight * product.quantity }, 0));
    setTotalPrice(products.reduce((acc, product) => { return acc + product.price * product.quantity }, 0));
    
  }, [])

  return (
    <React.Fragment>
      <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
        <TableCell>
          <IconButton
            aria-label="expand row"
            size="small"
            onClick={() => setOpen(!open)}
          >
            {open ? <SlArrowUp /> : <SlArrowDown />}
          </IconButton>
        </TableCell>
        <TableCell component="th" scope="row">{ order?.id }</TableCell>
        { user?.role === "ADMIN" && (
          <>
            <TableCell align="right">{order?.user.name}</TableCell>
            <TableCell align="right">{order?.user.email}</TableCell>
          </>
        )}
        <TableCell align="right">{dateFormat(order?.creation_date)}</TableCell>
        <TableCell align="right">
          { totalWeight } г
        </TableCell>
        <TableCell align="right">
          { totalPrice } грн
        </TableCell>
        <TableCell align="right">
          {!order?.confirmed && (
            <>
            {user.role === "ADMIN" && (
              <Button variant="outline-success" onClick={ handleConfirmOrder }>
                Підтвердити
              </Button>
            )}
            {user.role === "USER" && (
              <span className="text-danger">Не підтверждено</span>
            )}
            </>
          )}
          {order?.confirmed && !order?.paid && (
            <>
              {user.role === "ADMIN" && user.id !== order?.user.id && (
                <span className="text-danger">Підтверждено, але не сплачено</span>
              )}
              {(user.role === "USER" || user.id === order?.user.id) && (
                <Button variant="outline-success" onClick={ handlePaidOrder }>
                  Сплатити
                </Button>
              )}
            </>
          )}
          {order?.confirmed && order?.paid && (
            <span className="text-success">Підтверждено та сплачено</span>
          )}
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box sx={{ margin: 1 }}>
              <Typography variant="h6" gutterBottom component="div">
                Деталі замовлення
              </Typography>
              <Table size="small" aria-label="purchases">
                <TableHead>
                  <TableRow>
                    <TableCell>Назва</TableCell>
                    <TableCell>Кількість</TableCell>
                    <TableCell align="right">Вага (г)</TableCell>
                    <TableCell align="right">Ціна (грн)</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {
                  order?.products.map((product) => (
                    <TableRow key={product.id}>
                      <TableCell component="th" scope="row">
                        {product.name}
                      </TableCell>
                      <TableCell>{product.quantity}</TableCell>
                      <TableCell align="right">{product.weight * product.quantity} г</TableCell>
                      <TableCell align="right">{product.price * product.quantity} грн</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}

export default function OrdersTable({ orders, user }) {
  return (
    <TableContainer component={Paper}>
      <Table aria-label="collapsible table">
        <TableHead>
          <TableRow>
            <TableCell />
            <TableCell>Order#</TableCell>
            { user?.role === "ADMIN" && (
              <>
                <TableCell align="right">Імʼя</TableCell>
                <TableCell align="right">Пошта</TableCell>
              </>
            )}
            <TableCell align="right">Дата Замовлення</TableCell>
            <TableCell align="right">Загальна вага&nbsp;(г)</TableCell>
            <TableCell align="right">Загальна ціна&nbsp;(грн)</TableCell>
            <TableCell></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          { orders.map((order) => (
              <Row key={order.id} row={ order } user={ user } />
          )) }
        </TableBody>
      </Table>
    </TableContainer>
  );
}
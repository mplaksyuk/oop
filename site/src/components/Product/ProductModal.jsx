import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { useForm, useController} from "react-hook-form";

import Select from 'react-select';

import Modal from 'react-bootstrap/Modal'
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import { useEffect, useState } from 'react';
import Axios from '../../Axios';

export default function ProductModal ({ show, onHide }) {

    const [ ingredients, setIngredients ] = useState([]);

    const { register, handleSubmit, control, setValue} = useForm();

    const onSubmit = (data, e) => {
        Axios.post('/products/add', data);
    }

    const onError = (errors, e) => console.log(errors, e);

    const handleSelectChange = (selectedOptions) => {
        const selectedValues = selectedOptions.reduce((res, option) => {
            res.push({id : +option.value, name : option.label})
            return res;
        }, [])
        setValue('ingredients', selectedValues);
      };

    useEffect(() => {
        Axios.get('/ingredients').then(res => {
            const data = res.data.reduce((data, ingredient) => {
                data.push( { value : ingredient.id, label : ingredient.name } );
                return data;
            }, [])

            setIngredients(data);
        })
    }, [])

    return (
        <Modal show={ show } onHide={ onHide } backdrop="static" >

            <Modal.Header closeButton>
                <Modal.Title>Додати новий продукт</Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <Form onSubmit={handleSubmit(onSubmit, onError)}>
                    <Form.Group className="mb-3">
                        <Form.Label>Назва</Form.Label>
                        <Form.Control {...register('name', { required: true } )}/>
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Image URL</Form.Label>
                        <Form.Control {...register('image', { required: true } )}/>
                    </Form.Group>

                    <Row className="mb-3">
                        <Col>
                            <Form.Label>Вага</Form.Label>
                            <Form.Control type="number" {...register('weight', { required: true, valueAsNumber: true } )}/>
                        </Col>
                        <Col>
                            <Form.Label>Ціна</Form.Label>
                            <Form.Control type="number" {...register('price', { required: true, valueAsNumber: true } )}/>
                        </Col>
                    </Row>

                    <Select
                    { ...useController( { name : "ingredients", control, rules: { required: true } } ) }
                    className="mb-3"
                    closeMenuOnSelect={ false }
                    isMulti
                    onChange={handleSelectChange}
                    options={ ingredients }
                    />

                    <Button type="submit" variant="success">Додати</Button>
                </Form>
            </Modal.Body>

        </Modal>
    )
}
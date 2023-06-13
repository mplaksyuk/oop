import './Tables.css';

export const Tables = ( { name, arr } ) => {
    return (
        <table className="table" name= {name}>
            <thead>
                <tr>
                    {
                        arr.map(el => <th>{el}</th>)
                    }
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1</td>
                    <td>1</td>
                    <td>1</td>
                </tr>
                <tr>
                    <td>1</td>
                    <td>1</td>
                    <td>1</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>2</td>
                    <td>2</td>
                </tr>
            </tbody>
        </table>
    )
}
import { FC } from 'react';
import axios from 'axios';
import { format } from 'date-fns';
import LayoutAdmin from '../../../components/LayoutAdmin';
import { IMultipleResource } from '../../../interfaces/api-responses/IMultipleResource';
import { IPedido } from '../../../interfaces/IPedido';
import Link from 'next/link';

interface PedidoTablaProps {
    pedidos: IPedido[];
}

const PedidoTabla: FC<PedidoTablaProps> = ({ pedidos }) => {
    const filasPedido = pedidos.map((pedido) => (
        <tr key={pedido._id}>
            <th scope="row">{pedido._id}</th>
            <td>
                {pedido.cliente.nombre} {pedido.cliente.apellido}
            </td>
            <td>S/. {pedido.total}</td>
            <td>{format(Date.parse(pedido.fechaRegistro), 'dd/MM/yyyy')}</td>
            <td>{format(Date.parse(pedido.fechaRegistro), 'h:mm a')}</td>
            <td>{pedido.estado}</td>
            <td>
                <button className="btn btn-success">Procesar</button>
            </td>
        </tr>
    ));

    return (
        <LayoutAdmin>
            <div>
                <div className="row py-1">
                    <div className="col">
                        <h5>Pedidos ({pedidos.length})</h5>
                    </div>
                    <div className="col-auto">
                        <Link href="/admin/pedidos/pedidoRegistrar">
                            <a className="btn btn-primary">Registrar</a>
                        </Link>
                    </div>
                </div>
                <table className="table table-bordered table-striped">
                    <thead className="thead-light">
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Cliente</th>
                            <th scope="col">Total</th>
                            <th scope="col">Fecha</th>
                            <th scope="col">Hora</th>
                            <th scope="col">Estado</th>
                            <th scope="col">Opciones</th>
                        </tr>
                    </thead>
                    <tbody>{filasPedido}</tbody>
                </table>
            </div>
        </LayoutAdmin>
    );
};

export default PedidoTabla;

export const getServerSideProps = async () => {
    const res = await axios.get<IMultipleResource<IPedido>>('http://localhost:3000/api/v1/pedidos');
    const pedidos = res.data.data;

    return { props: { pedidos } };
};

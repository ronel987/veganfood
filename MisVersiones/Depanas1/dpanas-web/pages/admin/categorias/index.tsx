import { FC } from 'react';
import axios from 'axios';
import { format } from 'date-fns';
import LayoutAdmin from '../../../components/LayoutAdmin';
import { IMultipleResource } from '../../../interfaces/api-responses/IMultipleResource';
import { ICategoria } from '../../../interfaces/ICategoria';
import Link from 'next/link';

interface CategoriaTablaProps {
    categorias: ICategoria[];
}

const CategoriaTabla: FC<CategoriaTablaProps> = ({ categorias }) => {
    const filasCategoria = categorias.map(({ _id, productos, nombre, fechaRegistro }) => (
        <tr key={_id}>
            <th scope="row">{_id}</th>
            <td>{nombre}</td>
            <td>{productos.length}</td>
            <td>{format(Date.parse(fechaRegistro), 'dd/MM/yyyy')}</td>
            <td>
                <button className="btn btn-success me-2">Editar</button>
                <button className="btn btn-outline-danger">Eliminar</button>
            </td>
        </tr>
    ));

    return (
        <LayoutAdmin>
            <div>
                <div className="row py-1">
                    <div className="col">
                        <h5>Categorias ({categorias.length})</h5>
                    </div>
                    <div className="col-auto">
                        <Link href="/admin/categorias/categoriaRegistrar">
                            <a className="btn btn-primary">Registrar</a>
                        </Link>
                    </div>
                </div>
                <table className="table table-bordered table-striped">
                    <thead className="thead-light">
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Nombre</th>
                            <th scope="col">N.Productos</th>
                            <th scope="col">F.Registro</th>
                            <th scope="col">Opciones</th>
                        </tr>
                    </thead>
                    <tbody>{filasCategoria}</tbody>
                </table>
            </div>
        </LayoutAdmin>
    );
};

export default CategoriaTabla;

export const getServerSideProps = async () => {
    const res = await axios.get<IMultipleResource<ICategoria>>(
        'http://localhost:3000/api/v1/categorias'
    );
    const categorias = res.data.data;

    return { props: { categorias } };
};

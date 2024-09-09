import LayoutClient from '../../components/LayoutClient';
import { IMultipleResource } from '../../interfaces/api-responses/IMultipleResource';
import { IProducto } from '../../interfaces/IProducto';
import { FC } from 'react';
import axios from 'axios';
import Image from 'next/image';
import Link from 'next/link';

interface ProductoTablaProps {
    productos: IProducto[];
}

const HomePage: FC<ProductoTablaProps> = ({ productos }) => {
    const cardProducto = productos.map((producto) => (
        <Link key={producto._id} href="/client/ordenProducto">
            <div className="col-auto">
                <div className="card" style={{ cursor: 'pointer' }}>
                    <Image
                        className="img-thumbnail"
                        src={producto.imagenUrl}
                        alt="Producto Imagen"
                        width={300}
                        height={300}
                        layout="responsive"
                    />
                    <div className="card-body">
                        <p className="card-title">{producto.nombre}</p>
                        <p className="card-card-subtitle mb-0">S/. {producto.precio}</p>
                    </div>
                </div>
            </div>
        </Link>
    ));

    return (
        <LayoutClient>
            <div>
                <div className="container">
                    <div className="row py-5 row-cols-auto">
                        <div className="col">
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                width="50"
                                height="50"
                                fill="currentColor"
                                className="bi bi-layout-text-sidebar-reverse"
                                viewBox="0 0 16 16"
                            >
                                <path d="M12.5 3a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1h5zm0 3a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1h5zm.5 3.5a.5.5 0 0 0-.5-.5h-5a.5.5 0 0 0 0 1h5a.5.5 0 0 0 .5-.5zm-.5 2.5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1h5z" />
                                <path d="M16 2a2 2 0 0 0-2-2H2a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2zM4 1v14H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h2zm1 0h9a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H5V1z" />
                            </svg>
                        </div>
                        <div className="col">
                            <h2>Productos</h2>
                        </div>
                    </div>
                    <div className="mb-3">
                        <select name="categoria" className="form-control">
                            <option value="2">Mostrar todos los productos</option>
                            <option value="1">Pizzas</option>
                            <option value="1">Guarniciones</option>
                            <option value="0">Bebidas</option>
                        </select>
                    </div>
                    <div className="row g-3 row-cols-lg-5">{cardProducto}</div>
                </div>
            </div>
        </LayoutClient>
    );
};

export default HomePage;

export const getServerSideProps = async () => {
    const res = await axios.get<IMultipleResource<IProducto>>(
        'http://localhost:3000/api/v1/productos'
    );
    const productos = res.data.data;

    return { props: { productos } };
};

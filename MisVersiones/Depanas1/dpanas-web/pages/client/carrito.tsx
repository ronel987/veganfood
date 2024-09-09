import Navbar from '../../components/Navbar';
import Link from 'next/link';
const Carrito = () => {
    return (
        <div>
            <Navbar />
            <div className="container">
                <h4 className="py-4">Orden: #Numero de orden</h4>
                <div className="row row-cols-auto">
                    <div className="col">
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            width="30"
                            height="30"
                            fill="currentColor"
                            className="bi bi-cart"
                            viewBox="0 0 16 16"
                        >
                            <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
                        </svg>
                    </div>
                    <div className="col">
                        <h5>1 Producto(s)</h5>
                    </div>
                    <div className="col">
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            width="30"
                            height="30"
                            fill="currentColor"
                            className="bi bi-cash-coin"
                            viewBox="0 0 16 16"
                        >
                            <path
                                fill-rule="evenodd"
                                d="M11 15a4 4 0 1 0 0-8 4 4 0 0 0 0 8zm5-4a5 5 0 1 1-10 0 5 5 0 0 1 10 0z"
                            />
                            <path d="M9.438 11.944c.047.596.518 1.06 1.363 1.116v.44h.375v-.443c.875-.061 1.386-.529 1.386-1.207 0-.618-.39-.936-1.09-1.1l-.296-.07v-1.2c.376.043.614.248.671.532h.658c-.047-.575-.54-1.024-1.329-1.073V8.5h-.375v.45c-.747.073-1.255.522-1.255 1.158 0 .562.378.92 1.007 1.066l.248.061v1.272c-.384-.058-.639-.27-.696-.563h-.668zm1.36-1.354c-.369-.085-.569-.26-.569-.522 0-.294.216-.514.572-.578v1.1h-.003zm.432.746c.449.104.655.272.655.569 0 .339-.257.571-.709.614v-1.195l.054.012z" />
                            <path d="M1 0a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h4.083c.058-.344.145-.678.258-1H3a2 2 0 0 0-2-2V3a2 2 0 0 0 2-2h10a2 2 0 0 0 2 2v3.528c.38.34.717.728 1 1.154V1a1 1 0 0 0-1-1H1z" />
                            <path d="M9.998 5.083 10 5a2 2 0 1 0-3.132 1.65 5.982 5.982 0 0 1 3.13-1.567z" />
                        </svg>
                    </div>
                    <div className="col">
                        <h5>Total: s/39.90</h5>
                    </div>
                    <div className="py-4"></div>
                    <table className="table table-bordered table-striped">
                        <thead className="thead-light">
                            <tr>
                                <th scope="col">Cant.</th>
                                <th scope="col">Producto</th>
                                <th scope="col">Presentación</th>
                                <th scope="col">Extras</th>
                                <th scope="col">Precio</th>
                                <th scope="col">Borrar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1</td>
                                <td>Pizza Pepperoni</td>
                                <td>Familiar</td>
                                <td>Extra queso</td>
                                <td>39.90</td>
                                <td>
                                    <button className="btn btn-outline-danger">Eliminar</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div className="mb-3 px-0">
                        <Link href="/client/datosCliente">
                            <a className="btn btn-warning">Ordenar</a>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Carrito;

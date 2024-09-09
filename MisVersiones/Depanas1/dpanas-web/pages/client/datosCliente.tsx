import LayoutClient from '../../components/LayoutClient';
import Link from 'next/link';

const DatosCliente = () => {
    return (
        <LayoutClient>
            <div className="container py-5">
                <h2>Datos de Cliente</h2>
                <form autoComplete="off" noValidate>
                    <h5>Método de entrega</h5>
                    <div className="col">
                        <div className="form-check">
                            <input
                                className="form-check-input"
                                type="radio"
                                name="domicilio"
                                id="domicilio"
                            />
                            <label className="form-check-label" htmlFor="domicilio">
                                Entrega a Domicilio
                            </label>
                        </div>
                        <div className="form-check">
                            <input
                                className="form-check-input"
                                type="radio"
                                name="sucursal"
                                id="sucursal"
                            />
                            <label className="form-check-label" htmlFor="sucursal">
                                Recoger en sucursal
                            </label>
                        </div>
                    </div>
                    <div className="mb-3 py-2">
                        <label>Nombre</label>
                        <input
                            type="text"
                            className="form-control"
                            name="txtNombre"
                        />
                    </div>
                    <div className="mb-3 py-2">
                        <label>Telefono/Celular</label>
                        <input
                            type="text"
                            className="form-control"
                            name="txtNombre"
                        />
                    </div>
                    <div className="mb-3 py-2">
                        <label>Dirección de entrega</label>
                        <input
                            type="text"
                            className="form-control"
                            name="txtNombre"
                        />
                    </div>
                    <div className="mb-3 px-0 py-3">
                        <Link href="/client/datosCliente">
                            <a className="btn btn-warning">Enviar Orden</a>
                        </Link>
                    </div>
                </form>
            </div>
        </LayoutClient>
    );
};

export default DatosCliente;

import LayoutClient from '../../components/LayoutClient';
import Link from 'next/link';
const OrdenProducto = () => {
    return (
        <LayoutClient>
            <div className="container py-5">
                <form autoComplete="off" noValidate>
                    <div className="mb-3">
                        <label htmlFor="Cantidad">¿Cuántas desea ordenar?</label>
                        <input
                            id="cantidad"
                            name="cantidad"
                            className="form-control"
                            type="number"
                        />
                    </div>
                    <div className="mb-3">
                        <label>Presentación</label>
                        <select name="presentación" className="form-control">
                            <option value="2">Personal</option>
                            <option value="1">Mediana</option>
                            <option value="0">Familiar</option>
                        </select>
                    </div>
                    <div className="mb-3 py-4">
                        <label>Ingredientes extra:</label>
                        <div className="row row-cols-auto">
                            <div className="col">
                                <div className="form-check">
                                    <input
                                        className="form-check-input"
                                        type="radio"
                                        name="queso"
                                        id="queso"
                                    />
                                    <label className="form-check-label" htmlFor="queso">
                                        Extra queso
                                    </label>
                                </div>
                                <div className="form-check">
                                    <input
                                        className="form-check-input"
                                        type="radio"
                                        name="tomate"
                                        id="tomate"
                                    />
                                    <label className="form-check-label" htmlFor="tomate">
                                        Tomate
                                    </label>
                                </div>
                                <div className="form-check">
                                    <input
                                        className="form-check-input"
                                        type="radio"
                                        name="champiñones"
                                        id="champiñones"
                                    />
                                    <label className="form-check-label" htmlFor="flexRadioDefault2">
                                        Champiñón
                                    </label>
                                </div>
                            </div>
                            <div className="col">
                                <div className="form-check">
                                    <input
                                        className="form-check-input"
                                        type="radio"
                                        name="jamon"
                                        id="jamon"
                                    />
                                    <label className="form-check-label" htmlFor="jamon">
                                        Extra jamón
                                    </label>
                                </div>
                                <div className="form-check">
                                    <input
                                        className="form-check-input"
                                        type="radio"
                                        name="oregano"
                                        id="oregano"
                                    />
                                    <label className="form-check-label" htmlFor="oregano">
                                        Orégano
                                    </label>
                                </div>
                                <div className="form-check">
                                    <input
                                        className="form-check-input"
                                        type="radio"
                                        name="pepperoni"
                                        id="pepperoni"
                                    />
                                    <label className="form-check-label" htmlFor="pepperoni">
                                        Pepperoni
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div className="text-warning">
                            <p>Elija solo 3</p>
                        </div>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="comentario">Comentario especial para el producto</label>
                        <input
                            id="comentario"
                            name="comentario"
                            className="form-control"
                            type="text"
                        />
                    </div>
                    <div className="mb-3 py-4">
                        <Link href="/client/carrito">
                            <a className="btn btn-warning">Agregar a la orden</a>
                        </Link>
                    </div>
                </form>
            </div>
        </LayoutClient>
    );
};

export default OrdenProducto;

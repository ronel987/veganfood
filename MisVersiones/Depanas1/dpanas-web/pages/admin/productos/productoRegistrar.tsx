import LayoutAdmin from "../../../components/LayoutAdmin";
import Link from "next/link";

const ProductoRegistrar = () => {
    return(
        <LayoutAdmin>
            <div className="producto-registrar">
                <header>
                    <div className="row">
                        <div className="col">
                            <p className="h2 text-secondary">Registrar producto</p>
                        </div>
                        <div className="col-auto">
                            <Link href="/admin/productos">
                                <a className="btn btn-outline-secondary">Volver</a>
                            </Link>
                        </div>
                    </div>
                </header>
                <form>
                    <div className="mb-3">
                        <input
                            type="text"
                            className="form-control"
                            name="txtNombre"
                            placeholder="Nombre"
                        />
                    </div>
                    <div className="mb-3">
                        <input
                            type="text"
                            className="form-control"
                            name="txtNombre"
                            placeholder="Descripción"
                        />
                    </div>
                    <div className="mb-3">
                        <input
                            type="text"
                            className="form-control"
                            name="txtNombre"
                            placeholder="Precio"
                        />
                    </div>
                    <div className="mb-3">
                        <input
                            type="text"
                            className="form-control"
                            name="txtNombre"
                            placeholder="Stock"
                        />
                    </div>
                    <div className="mb-3">
                        <input
                            type="text"
                            className="form-control"
                            name="txtNombre"
                            placeholder="Imagen URL"
                        />
                    </div>
                    <div className="mb-3">
                        <label>Estado</label>
                        <select
                            name="estado"
                            className="form-control"
                        >
                            <option value="1">Habilitado</option>
                            <option value="0">Deshabilitado</option>
                        </select>
                    </div>
                    <div className="py-1">
                        <button className="btn btn-block btn-primary" type="submit">
                            Registrar
                        </button>
                    </div>
                </form>
            </div>
        </LayoutAdmin>
    )
}

export default ProductoRegistrar;

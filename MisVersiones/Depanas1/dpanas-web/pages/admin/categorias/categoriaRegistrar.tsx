import LayoutAdmin from '../../../components/LayoutAdmin';
import Link from 'next/link';

const CategoriaRegistrar = () => {
    return (
        <LayoutAdmin>
            <div className="categoria-registrar">
                <header>
                    <div className="row">
                        <div className="col">
                            <p className="h2 text-secondary">Registrar categoria</p>
                        </div>
                        <div className="col-auto">
                            <Link href="/admin/categorias">
                                <a className="btn btn-outline-secondary">Volver</a>
                            </Link>
                        </div>
                    </div>
                </header>
                <form>
                    <div className="py-1">
                        <input
                            type="text"
                            className="form-control"
                            name="txtNombre"
                            placeholder="Nombre"
                        />
                    </div>
                    <div className="py-3">
                        <button className="btn btn-block btn-primary" type="submit">
                            Registrar
                        </button>
                    </div>
                </form>
            </div>
        </LayoutAdmin>
    );
};

export default CategoriaRegistrar;

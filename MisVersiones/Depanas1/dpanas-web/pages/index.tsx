import Navbar from '../components/Navbar';
import Link from 'next/link';
import Image from 'next/image';

const Index = () => {
    return (
        <div>
            <Navbar />
            <div className="container">
                <div className="row py-5">
                    <div className="col-md-12">
                        <div className="card card-body bg-ligth py-5 px-5">
                            <div className="row">
                                <div className="col-md-4">
                                    <Image
                                        src="/pizza.jpg"
                                        alt=""
                                        width="1000"
                                        height="600"
                                        className="img-fluid"
                                    />
                                </div>
                                <div className="col-md-8 text-center">
                                    <h1>Iniciar Sesión</h1>
                                    <div className="d-grid gap-2 col-6 mx-auto py-3 ">
                                        <form autoComplete="off">
                                            <div className="mb-3 py-3">
                                                <input
                                                    type="text"
                                                    className="form-control"
                                                    placeholder="Usuario"
                                                />
                                            </div>
                                        </form>
                                        <form autoComplete="off">
                                            <div className="mb-3 py-3">
                                                <input
                                                    type="password"
                                                    className="form-control"
                                                    placeholder="Contraseña"
                                                />
                                            </div>
                                        </form>
                                        <div className="d-grid col-8 mx-auto py-3">
                                            <Link href="/client">
                                                <a className="btn btn-warning" role="button">
                                                    Ingresar
                                                </a>
                                            </Link>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Index;

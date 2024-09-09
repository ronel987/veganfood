import React, { Component } from 'react';
import Link from 'next/link';

class Sidevar extends Component {
    render() {
        return (
            <div className="col-2">
                <p className="h5 text-secondary">Modulos</p>
                <div className="card">
                    <ul className="list-group list-group-flush">
                        <li className="list-group-item">
                            <Link href="/admin/categorias">
                                <a className="text-secondary">CATEGORIAS</a>
                            </Link>
                        </li>
                        <li className="list-group-item">
                            <Link href="/admin/productos">
                                <a className="text-secondary">PRODUCTOS</a>
                            </Link>
                        </li>
                        <li className="list-group-item">
                            <Link href="/admin/pedidos">
                                <a className="text-secondary">PEDIDOS</a>
                            </Link>
                        </li>
                    </ul>
                </div>
            </div>
        );
    }
}

export default Sidevar;

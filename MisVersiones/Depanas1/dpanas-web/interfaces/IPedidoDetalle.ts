import { IProducto } from './IProducto';

export interface IPedidoDetalle {
    _id: string;
    producto: IProducto;
    precioUnitario: number;
    cantidad: number;
    subtotal: number;
}

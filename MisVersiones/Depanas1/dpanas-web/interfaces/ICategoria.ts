import { IProducto } from './IProducto';

export interface ICategoria {
    _id: string;
    productos: IProducto[];
    nombre: string;
    fechaRegistro: string;
}

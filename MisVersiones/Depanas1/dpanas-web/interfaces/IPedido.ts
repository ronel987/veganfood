import { ICliente } from './ICliente';
import { IDireccion } from './IDireccion';
import { IPedidoDetalle } from './IPedidoDetalle';

export interface IPedido {
    _id: string;
    cliente: ICliente;
    direccion: IDireccion | null;
    detalles: IPedidoDetalle[];
    total: number;
    fechaRegistro: string;
    estado: string;
}

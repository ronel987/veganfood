
import SidebarHome from './SidebarHome';

const LayoutClient = ({ children }) => {
    return (
        <div className="root-layout">
            <div className="row">
                <SidebarHome />
                <div className="col-9">{children}</div>
            </div>
        </div>
    );
};

export default LayoutClient;
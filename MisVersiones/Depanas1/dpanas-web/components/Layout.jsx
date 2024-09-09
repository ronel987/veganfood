import Navbar from './Navbar';
import Sidebar from './Sidebar';

const Layout = ({ children }) => {
    return (
        <div className="root-layout">
            <Navbar />
            <div className="container">
                <div className="py-4">
                    <div className="row">
                        <Sidebar />
                        <div className="col-9">{children}</div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Layout;

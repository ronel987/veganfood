import Navbar from './Navbar';
import Sidebar from './Sidebar';

const LayoutAdmin = ({ children }) => {
    return (
        <div className="root-layout">
            <Navbar />
            <div className="container">
                <div className="py-4">
                    <div className="row">
                        <Sidebar />
                        <div className="col-10">{children}</div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LayoutAdmin;

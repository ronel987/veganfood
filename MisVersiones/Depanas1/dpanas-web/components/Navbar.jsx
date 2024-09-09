import Link from 'next/link';

const Navbar = () => (
    <nav className="navbar navbar-light bg-light">
        <div className="container-fluid">
            <div className="container-fluid text-center">
                <Link href="/client">
                    <a className="navbar-brand h1 text-warning">D Panas</a>
                </Link>
            </div>
        </div>
    </nav>
);

export default Navbar;

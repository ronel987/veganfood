import React, {Component} from 'react';
import Layout from '../../components/LayoutAdmin';
import Image from 'next/image';

class Home extends Component {
    render() {
        return (
            <Layout>
                <Image src="/pizza.jpg" alt="" width="1000" height="600" className="img-fluid" />
            </Layout>
        )
    }
}

export default Home;



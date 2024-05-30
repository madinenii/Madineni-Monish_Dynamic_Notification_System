
import React from 'react';
import Navbar from '../Components/Navbar';
import ContactCard from '../Pages/ContactCard'; // Adjust path as needed

const AboutPage = () => {
    return (
        <>
            <Navbar />
            <div className="jumbotron text-center">
                <div className="display-4">About Us</div>
                <p className="lead">This is a simple about us page</p>
            </div>
            <div className="container">
                <section>
                    <ContactCard />
                    {/* <p>
                        Lorem ipsum dolor sit, amet consectetur adipisicing elit. Blanditiis debitis sit voluptatibus ea molestiae architecto nulla necessitatibus distinctio velit est. Modi et eum ullam repellat, quas saepe asperiores sapiente cum iste repellendus sed eveniet est quo nobis, tempore beatae veritatis placeat fugit necessitatibus! Placeat, quam veritatis. Ratione unde et eligendi enim veritatis, quis quod! Facilis magni eaque dolore dolor minus adipisci laudantium blanditiis. Quia fugit laudantium quidem iure autem cum molestias maxime asperiores perferendis dolore consectetur labore, laborum ipsam accusamus dolorum. Eos cumque, consequatur, consequuntur necessitatibus excepturi nesciunt dolorem, at quis enim ut sint ipsam. Sequi corrupti ad quas consectetur?
                    </p>
                    <p>
                        Lorem ipsum dolor sit, amet consectetur adipisicing elit. Doloribus, minima recusandae. Quas, autem sit sint, distinctio repudiandae quisquam molestiae aperiam cumque nesciunt consequuntur accusamus recusandae quo in suscipit soluta. Laborum nulla recusandae architecto doloremque praesentium eum iusto? Soluta pariatur perferendis sit suscipit rem, tempore non tenetur aliquam voluptate ut facere velit odit. Optio soluta eligendi doloremque, iusto nam nobis magni? Nemo fugit delectus magnam dicta praesentium nam exercitationem optio illum, eaque possimus voluptatum voluptatibus saepe assumenda iusto excepturi ab ducimus, tenetur itaque nostrum necessitatibus vel, dignissimos quibusdam debitis! Iusto laboriosam officia dicta veritatis amet eligendi vitae ab, commodi ipsam dolorum!
                    </p> */}
                </section>
            </div>
        </>
    );
};

export default AboutPage;

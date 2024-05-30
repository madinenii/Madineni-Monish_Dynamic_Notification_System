import CategoryList from "../Components/CategoryList";
import Display from "./Inventory/Display";
import Header from "../Components/Header";
import Navbar from "../Components/Navbar"

const HomePage=()=>{
    return(
        <>
        <Navbar/>
        <Header/>
        {/* <CategoryList/> */}
        <Display/>
        </>
    )
}

export default HomePage;
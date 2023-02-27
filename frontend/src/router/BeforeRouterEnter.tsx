import {useLocation, useRoutes} from "react-router-dom";
import router from "./index";
import Home from "../views/Home"
import EntrancePage from "../views/Login/EntrancePage"

const BeforeRouterEnter = () => {
    const outlet = useRoutes(router);

    const location = useLocation();
    const user_token = localStorage.getItem('user_token');
    const user_info = localStorage.getItem('user_info');
    if (location.pathname === "/entrance" && user_token && user_info) {
        return <Home />;
    }
    if (location.pathname !== "/entrance" && !(user_token && user_info)) {
        return <EntrancePage />;
    }

    return outlet;
}

export default BeforeRouterEnter;
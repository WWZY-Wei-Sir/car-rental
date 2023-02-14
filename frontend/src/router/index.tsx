import React, {lazy} from "react";
import {Navigate} from "react-router-dom";

const EntrancePage = lazy(() => import("../views/Login/EntrancePage"));
const Test = lazy(() => import("../views/Test"));
const Home = lazy(() => import("../views/Home"));
const Error = lazy(() => import("../views/Error"));

const loadingElement = (element: JSX.Element) => (
    <React.Suspense fallback={<div>loding...</div>}>
        {element}
    </React.Suspense>
)

const routes: object[] = [
    {
        path: "/",
        element: <Navigate to={"/Home"}/>
    },
    {
        path: "/entrance",
        element: loadingElement(<EntrancePage />)
    },
    {
        path: "/Home",
        element: loadingElement(<Home />)
    },
    {
        path: "/Test",
        element: loadingElement(<Test />)
    },
    {
        path: "/404",
        element: loadingElement(<Error />)
    },
    {
        path: "/*",
        element: <Navigate to={"/404"}/>
    }
];

export default routes;
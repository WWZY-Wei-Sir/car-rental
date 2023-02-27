import React, {lazy} from "react";
import {Navigate} from "react-router-dom";

const EntrancePage = lazy(() => import("../views/Login/EntrancePage"));
const Test = lazy(() => import("../views/Test"));
const Home = lazy(() => import("../views/Home"));
const Error = lazy(() => import("../views/Error"));
const UserManager = lazy(() => import("../views/UserManager"));

const loadingElement = (element: JSX.Element) => (
    <React.Suspense fallback={<div>loding...</div>}>
        {element}
    </React.Suspense>
)

const routes: object[] = [
    {
        path: "/entrance",
        element: loadingElement(<EntrancePage />)
    },
    {
        path: "/",
        element: <Navigate to={"/Test"}/>
    },
    {
        path: "/",
        element: <Home />,
        children: [
            {
                path: "/Test",
                element: loadingElement(<Test />)
            },
            {
                path: "/userManager",
                element: loadingElement(<UserManager />)
            }
        ]
    },
    {
        path: "/404",
        element: loadingElement(<Error />)
    },
    {
        path: "/*",
        element: <Navigate to={"/Test"}/>
    }
];

export default routes;
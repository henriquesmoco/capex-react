import './App.css'
import 'primereact/resources/themes/lara-light-teal/theme.css'
import {createBrowserRouter} from "react-router";
import {RouterProvider} from "react-router/dom";
import {PrimeReactProvider} from 'primereact/api';
import RootLayout from "./layout/RootLayout.jsx";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import LoggedUserProvider from "./context/user/LoggedUserProvider.jsx";
import ListRequestsPage from "./pages/ListRequestsPage.jsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <RootLayout/>,
        children: [
            {path: "/", element: <ListRequestsPage />},
            {path: "/unauthorized", element: <div><h1 className="text-4xl font-bold">Unauthorized</h1><p>Select an user and try again.</p></div>},
            {path: "/about", element: <h1>About</h1>},
        ]
    },
]);

const queryClient = new QueryClient()

function App() {
    return (
        <QueryClientProvider client={queryClient}>
            <PrimeReactProvider>
                <LoggedUserProvider>
                    <RouterProvider router={router}/>
                </LoggedUserProvider>
            </PrimeReactProvider>
        </QueryClientProvider>
    )
}

export default App

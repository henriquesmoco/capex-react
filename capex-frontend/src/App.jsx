import './App.css'
import 'primereact/resources/themes/lara-light-teal/theme.css'
import { createBrowserRouter } from "react-router";
import { RouterProvider } from "react-router/dom";
import { PrimeReactProvider } from 'primereact/api';
import RootLayout from "./layout/RootLayout.jsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <RootLayout />,
        children: [
            { path: "/", element: <h1>Home</h1>},
            { path: "/about", element: <h1>About</h1>},
        ]
    },
]);

function App() {
  return (
      <PrimeReactProvider>
        <RouterProvider router={router} />
      </PrimeReactProvider>
  )
}

export default App

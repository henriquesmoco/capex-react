import './App.css'
import 'primereact/resources/themes/lara-light-teal/theme.css'
import { createBrowserRouter } from "react-router";
import { RouterProvider } from "react-router/dom";
import { PrimeReactProvider } from 'primereact/api';

const router = createBrowserRouter([
    {
        path: "/",
        element: <div>Capex React 2</div>,
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

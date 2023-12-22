import { RouterProvider, createBrowserRouter } from "react-router-dom";
import Login from "./page/Login";
import Home from "./page/Home";

function App() {

  const routes = createBrowserRouter([
    {
      path : "/",
      element : <Login/>
    },
    {
      path : "/bank",
      element : <Home/>
    }
  ])

  return (
      <RouterProvider router={routes} />
  );
}

export default App;

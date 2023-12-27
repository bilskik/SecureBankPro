import { RouterProvider, createBrowserRouter } from "react-router-dom";
import Login from "./page/Login";
import Dashboard from "./page/Dashboard";
import { dashboardUserLoader } from "./util/loader/dashboardUserLoader";
import Transfer from "./component/transfer/Transfer";


function App() {

  const routes = createBrowserRouter([
    {
      path : "/login",
      element : <Login/>
    },
    {
      path : "/",
      loader : dashboardUserLoader,
      element : <Dashboard/>,
    },
    {
      path : "/payment",
      element : <Transfer/>
    }
  ])

  return (
      <RouterProvider router={routes} />
  );
}

export default App;

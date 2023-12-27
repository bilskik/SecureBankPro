import { RouterProvider, createBrowserRouter } from "react-router-dom";
import Login from "./page/Login";
import Dashboard from "./page/Dashboard";
import { dashboardUserLoader } from "./util/loader/dashboardUserLoader";
import Transfer from "./page/Transfer";
import UserDetails from "./page/UserDetails";
import { userDetailsLoader } from "./util/loader/userDetailsLoader";


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
    },
    {
      path : "/details",
      loader : userDetailsLoader,
      element : <UserDetails/>
    }
  ])

  return (
      <RouterProvider router={routes} />
  );
}

export default App;

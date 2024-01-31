import { RouterProvider, createBrowserRouter, redirect } from "react-router-dom";
import Login from "./page/Login";
import Dashboard from "./page/Dashboard";
import { dashboardUserLoader } from "./util/loader/dashboardUserLoader";
import Transfer from "./page/Transfer";
import UserDetails from "./page/UserDetails";
import { userDetailsLoader } from "./util/loader/userDetailsLoader";
import "./app.css"
import { DASHBOARD_PAGE, DETAILS_PAGE, LOGIN_PAGE, PAYMENT_PAGE, RESET_PASSWORD_PAGE, USER_DATA } from "./common/url/urlMapper";
import NavComp from "./component/navbar/NavComp";
import { transferLoader } from "./util/loader/transferLoader";
import { navLoader } from "./util/loader/navLoader";
import { getData } from "./common/api/apiCall";
import ResetPassword from "./component/password/ResetPassword";
import AuthProvider, { AuthContext } from "./util/context/AuthProvider";

function App() {

  const routes = createBrowserRouter([
    {
      path : LOGIN_PAGE,
      element : <Login/>
    },
    {
      path : DASHBOARD_PAGE,
      element : <NavComp/>,
      children : [
        { 
            path : "",
            loader : dashboardUserLoader,
            element : <Dashboard/>
        },
        {
          path : PAYMENT_PAGE,
          loader : transferLoader,
          element : <Transfer/>
        },
        {
          path : DETAILS_PAGE,
          loader : userDetailsLoader,
          element : <UserDetails/>
        }
      ]
    }
  ])

  return (
    <AuthProvider>
      <RouterProvider router={routes} />
    </AuthProvider>
  );
}

export default App;

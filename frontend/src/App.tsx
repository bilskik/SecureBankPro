import { RouterProvider, createBrowserRouter, redirect } from "react-router-dom";
import Login from "./features/login/Login";
import Transfer from "./features/transfer/Transfer";
import UserDetails from "./features/details/UserDetails";
import { userDetailsLoader } from "./util/loader/userDetailsLoader";
import "./app.css"
import { DASHBOARD_PAGE, DETAILS_PAGE, LOGIN_PAGE, PAYMENT_PAGE, RESET_PASSWORD_PAGE, USER_DATA } from "./config/urlMapper";
import NavComp from "./features/nav/NavComp";
import AuthProvider, { AuthContext } from "./util/context/AuthProvider";
import Dashboard from "./features/dashboard/Dashboard";

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
            element : <Dashboard/>
        },
        {
          path : PAYMENT_PAGE,
          element : <Transfer/>
        },
        {
          path : DETAILS_PAGE,
          loader : userDetailsLoader,
          element : <UserDetails/>
        }
      ]
    }, 
  ])

  return (
    <AuthProvider>
      <RouterProvider router={routes} />
    </AuthProvider>
  );
}

export default App;

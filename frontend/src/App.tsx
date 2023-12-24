import { RouterProvider, createBrowserRouter } from "react-router-dom";
import Login from "./page/Login";
import Dashboard from "./page/Dashboard";
import { dashboardUserLoader } from "./util/dashboardUserLoader";


function App() {

  const routes = createBrowserRouter([
    {
      path : "/login",
      element : <Login/>
    },
    {
      path : "/",
      loader : dashboardUserLoader,
      element : <Dashboard/>
    }
  ])

  return (
      <RouterProvider router={routes} />
  );
}

export default App;

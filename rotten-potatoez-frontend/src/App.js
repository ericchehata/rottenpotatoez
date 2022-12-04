import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import NavBar from "./components/NavBar";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import Signin from "./pages/Signin";
import Signup from "./pages/Signup";

const theme = createTheme({
  palette: {
    primary: {
      main: '#454545',
      dark: '#000000',
      contrastText: '#fff',
    },
    secondary: {
      main: '#f44336',
      dark: '#ba000d',
      contrastText: '#fff',
    }
  },
});

function App() {
  return (
    <BrowserRouter>
      <ThemeProvider theme={theme}>
        <NavBar/>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/signin" element={<Signin />} />
            <Route path="/signup" element={<Signup />} />
          </Routes>
      </ThemeProvider>
    </BrowserRouter>
  
  );
}

export default App;
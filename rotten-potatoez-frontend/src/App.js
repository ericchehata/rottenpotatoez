import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import NavBar from "./components/NavBar";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import Signin from "./pages/Signin";
import Signup from "./pages/Signup";
import Profile from "./pages/Profile";
import Movie from "./pages/Movie";
import MyReviews from "./pages/MyReviews";
import CreateMovie from "./pages/CreateMovie";

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
            <Route path="/profile" element={<Profile />} />
            <Route path="/movie/:id" element={<Movie />} />
            <Route path="/my-reviews" element={<MyReviews />} />
            <Route path="/add-movie" element={<CreateMovie />} />
          </Routes>
      </ThemeProvider>
    </BrowserRouter>
  
  );
}

export default App;
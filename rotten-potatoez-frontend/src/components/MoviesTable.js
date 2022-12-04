import * as React from "react";
import axios from "axios";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TablePagination,
  TableRow,
  Paper,
} from "@mui/material";
import Genres from "./Genres";
import EnhancedTableHead from "./EnhancedTableHead";
import MoviesTableStyle from "../styles/MoviesTableStyle";

const MoviesTable = () => {
  const [movies, setMovies] = React.useState([]);
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);
  const [order, setOrder] = React.useState("asc");
  const [orderBy, setOrderBy] = React.useState("Title");

  const headCells = [
    { label: "Title", property: "title" },
    { label: "Duration (min)", property: "duration" },
    { label: "Release Date", property: "releaseDate" },
    { label: "Genres", property: "genres" },
  ];

  React.useEffect(() => {
    axios.get("movies").then((response) => {
      setMovies(response.data);
    });
    // eslint-disable-next-line
  }, []);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === "asc";
    setOrder(isAsc ? "desc" : "asc");
    setOrderBy(property);
  };

  const descendingComparator = (a, b, orderBy) => {
    if (b[orderBy] < a[orderBy]) {
      return -1;
    }
    if (b[orderBy] > a[orderBy]) {
      return 1;
    }
    return 0;
  };

  const getComparator = (order, orderBy) => {
    return order === "desc"
      ? (a, b) => descendingComparator(a, b, orderBy)
      : (a, b) => -descendingComparator(a, b, orderBy);
  };

  const stableSort = (array, comparator) => {
    const stabilizedThis = array.map((el, index) => [el, index]);
    stabilizedThis.sort((a, b) => {
      const order = comparator(a[0], b[0]);
      if (order !== 0) {
        return order;
      }
      return a[1] - b[1];
    });
    return stabilizedThis.map((el) => el[0]);
  };

  return (
      <Paper sx={MoviesTableStyle.container}>
        <TableContainer>
          <Table sx={MoviesTableStyle.table} aria-label="simple table">
            <EnhancedTableHead
              headCells={headCells}
              order={order}
              orderBy={orderBy}
              onRequestSort={handleRequestSort}
            />
            <TableBody>
              {stableSort(movies, getComparator(order, orderBy))
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((movie) => (
                  <TableRow
                    key={movie.id}
                    sx={MoviesTableStyle.tableRow}
                  >
                    <TableCell component="th" scope="row">
                      {movie.title} ({movie.releaseDate.split("-")[0]})
                    </TableCell>
                    <TableCell>{movie.duration}</TableCell>
                    <TableCell>{movie.releaseDate}</TableCell>
                    <TableCell>
                      <Genres genres={movie.genres} />
                    </TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[10, 25, 50]}
          component="div"
          count={movies.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </Paper>
  );
};

export default MoviesTable;

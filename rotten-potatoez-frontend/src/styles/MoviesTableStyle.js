const MoviesTableStyle = {
    container: { 
      width: "90%", 
      mt: "3rem",
      mb: "3rem",
      mr: "5%",
      ml: "5%",
    },
    table: {
      minWidth: 650,
    },
    tableRow: {
      "&:last-child td, &:last-child th": { 
        border: 0 
      }
    },
    tableHeaderCell: {
      fontWeight: 700,
    }
}

export default MoviesTableStyle;
import {
  Box,
  TableCell,
  TableHead,
  TableRow,
  TableSortLabel,
} from "@mui/material";
import { visuallyHidden } from "@mui/utils";

const EnhancedTableHead = (props) => {
  const { headCells, order, orderBy, onRequestSort } = props;
  const createSortHandler = (property) => (event) => {
    property !== "genres" && onRequestSort(event, property);
  };

  return (
    <TableHead>
      <TableRow>
        {headCells.map((headCell) => (
          <TableCell
            key={headCell.property}
            sortDirection={orderBy === headCell.property ? order : false}
          >
              <TableSortLabel
                active={orderBy === headCell.property}
                direction={orderBy === headCell.property ? order : "asc"}
                onClick={createSortHandler(headCell.property)}
              >
                {headCell.label}
                {orderBy === headCell.property ? (
                  <Box component="span" sx={visuallyHidden}>
                    {order === "desc"
                      ? "sorted descending"
                      : "sorted ascending"}
                  </Box>
                ) : null}
              </TableSortLabel>
          </TableCell>
        ))}
      </TableRow>
    </TableHead>
  );
};

export default EnhancedTableHead;

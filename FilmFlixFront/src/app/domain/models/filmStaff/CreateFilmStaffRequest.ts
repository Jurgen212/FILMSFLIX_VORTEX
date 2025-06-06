import { StaffRol } from "../../enums/StaffRol";

export interface CreateFilmStaffRequest {
    name: string;
    staffRol: StaffRol;
}

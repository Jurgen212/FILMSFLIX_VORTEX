import { Component, inject, Input } from '@angular/core';
import { UserResponse } from '../../../../domain/models/user/UserResponse';
import { TicketResponse } from '../../../../domain/models/ticket/TicketResponse';
import { PaymentMethod } from '../../../../domain/enums/PaymentMethod';
import { UserService } from '../../../../infraestructure/user/user.service';
import { FunctionService } from '../../../../infraestructure/function/function.service';
import { ToastrService } from 'ngx-toastr';
import { TicketService } from '../../../../infraestructure/ticket/ticket.service';

@Component({
  selector: 'app-users',
  standalone: false,
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent {
  users: UserResponse[] = [];
  tickets: TicketResponse[] = [];

  private userServ = inject(UserService);
  private ticketServ = inject(TicketService);
  private toastServ = inject(ToastrService);

  filteredUsers: UserResponse[] = [];
  expandedUsers: Set<number> = new Set();
  searchTerm: string = '';
  selectedRole: string = 'all';

  ngOnInit() {
    this.getUsersFromDb();
    this.getTicketsFromDb();
  }

  getUsersFromDb(){
    this.userServ.getAll().subscribe({
      next: (response: UserResponse[]) => {
        this.users = response;
        this.filteredUsers = [...this.users];
        this.filterUsers();
      },
      error: (error: any) => {
        console.error('Error fetching users:', error);
        this.toastServ.error(error["details"]);
      }
    })
  }

  getTicketsFromDb(){
    this.ticketServ.getAllTickets().subscribe({
      next: (response: TicketResponse[]) => {
        this.tickets = response;
      },
      error: (error: any) => {
        console.error('Error fetching tickets:', error);
        this.toastServ.error(error["details"]);
      }
    });
  }


  get totalUsers(): number {
    return this.users.length;
  }

  get totalTickets(): number {
    return this.tickets.length;
  }

  filterUsers() {
    let filtered = [...this.users];

    // Filter by search term
    if (this.searchTerm.trim()) {
      const term = this.searchTerm.toLowerCase();
      filtered = filtered.filter(user =>
        user.name.toLowerCase().includes(term) ||
        user.lastName.toLowerCase().includes(term) ||
        user.email.toLowerCase().includes(term)
      );
    }

    // Filter by role
    if (this.selectedRole !== 'all') {
      filtered = filtered.filter(user => user.role.toLowerCase() === this.selectedRole);
    }

    this.filteredUsers = filtered;
  }

  filterByRole(role: string) {
    this.selectedRole = role;
    this.filterUsers();
  }

  toggleUserExpanded(userId: number) {
    if (this.expandedUsers.has(userId)) {
      this.expandedUsers.delete(userId);
    } else {
      this.expandedUsers.add(userId);
    }
  }

  isUserExpanded(userId: number): boolean {
    return this.expandedUsers.has(userId);
  }

  getUserTickets(userId: number): TicketResponse[] {
    return this.tickets.filter(ticket => ticket.user.id === userId);
  }

  getUserTicketCount(userId: number): number {
    return this.getUserTickets(userId).length;
  }

  getUserTotalSpent(userId: number): number {
    return this.getUserTickets(userId).reduce((total, ticket) => total + ticket.total, 0);
  }

  getRoleClass(role: string): string {
    return `role-${role.toLowerCase()}`;
  }

  getPaymentMethodClass(method: PaymentMethod): string {
    return `payment-${method + "".toLowerCase().replace('_', '-')}`;
  }

  formatPaymentMethod(method: PaymentMethod): string {
      return method + "";
    }


  formatDate(date: Date): string {
    return new Date(date).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric'
    });
  }

  trackByUserId(index: number, user: UserResponse): number {
    return user.id;
  }

  trackByTicketId(index: number, ticket: TicketResponse): number {
    return ticket.id;
  }
}

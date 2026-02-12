import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { computed, signal } from '@angular/core';
import { Observable } from 'rxjs';

export interface InviteCodeDTO {
  code: string;
  gameId: number;
  expiresAt: string; 
}

@Injectable({ providedIn: 'root' })
export class InviteCodeService {
    private inviteCode = signal<InviteCodeDTO | null>(null);

    constructor(private http: HttpClient) {}

    getInviteCodeSignal() {
        return this.inviteCode.asReadonly();
    }
    setInviteCode(code: InviteCodeDTO) {
        this.inviteCode.set(code);
    }

 
    //genera id aleatiorio de invitacion
    fetchInviteCode(gameId: number): Observable<InviteCodeDTO> {
        return this.http.post<InviteCodeDTO>(`/api/invite-code/generate/${gameId}`, {});
    }

    //validar codigo de invitacion
    validateCode(code: string): Observable<number> {
        return this.http.get<number>(`/api/invite-code/validate/${code}`);
    }


}
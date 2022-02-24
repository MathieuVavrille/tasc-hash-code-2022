Vars:
	Person i (i=1..N):
		L_i_j >= 0, skill j of a person i (j=1..K)
		LN_i_j_k, complete with levelups in skills, LN_i_j_1 = L_i_j
	
	Project k (k = 1..M):
		D_k, Nb days to complete
		S_k, base score
		B_k, best before day (the idea is to first sort projects in the increasing order)
		R_k, NbRoles
		R_j_k>=0, requirement skill level for each role
		RST_k, real start date (-1 if the project isn't commences)
		
	Assignments:
		A_i_j_k, Boolean person i assigned to project k to a task j
		T_i_k, the date when a person i can start project i, T_i_1 = 0
		D_j_k in [0,1] - if there's a mentor, then there's a bonus +1 to a skill j for this task
		
Constraints (without mentors and levelups):
	any i,k: sum[l=1..K](A_i_j_k) =< 1 		(the person cannot be assigned to handle more than one task in a project)
	A_i_j_k = 1 => (L_i_j >= R_j_k)
	sum[i=1..N][j=1..M](A_i_j_k) = 0 OR sum[i=1..N][j=1..M](A_i_j_k) = R_k (either the project has enough people to commence or not done at all)
	sum[i=1..N][j=1..M](A_i_j_k) = 0 => RST_k = -1 (if project isn't commenced then there's no real startdate)
	sum[i=1..N][j=1..M](A_i_j_k) = 0 => RST_k = max[i=1..N](T_i_k)
	k>1: T_i_k = max[l=1..k-1,j=1..K](A_i_j_k * (RST_k + D_k))
	
Constraints (without mentors):
	any i,k: sum[l=1..K](A_i_j_k) =< 1 		(the person cannot be assigned to handle more than one task in a project)
	A_i_j_k = 1 => (LN_i_j_k >= R_j_k)
	k>1: LN_i_j_k = L_i_j + sum[l=1..k-1](1 * [A_i_j_k = 1 AND LN_i_j_k =< R_j_k])
	sum[i=1..N][j=1..M](A_i_j_k) = 0 OR sum[i=1..N][j=1..M](A_i_j_k) = R_k (either the project has enough people to commence or not done at all)
	sum[i=1..N][j=1..M](A_i_j_k) = 0 => RST_k = -1 (if project isn't commenced then there's no real startdate)
	sum[i=1..N][j=1..M](A_i_j_k) = 0 => RST_k = max[i=1..N](T_i_k)
	k>1: T_i_k = max[l=1..k-1,j=1..K](A_i_j_k * (RST_k + D_k))
	
Constraints (with mentors):
	any i,k: sum[l=1..K](A_i_j_k) =< 1 		(the person cannot be assigned to handle more than one task in a project)
	any j,k: D_j_k = max[i in 1..N]([LN_i_j_k >= R_j_k]*1)		(if there's at least one person within the project with a skill requirement met, add a bonus point to D_j_k)
	A_i_j_k = 1 => (LN_i_j_k + D_j_k >= R_j_k)
	k>1: LN_i_j_k = L_i_j + sum[l=1..k-1](1 * [A_i_j_k = 1 AND LN_i_j_k =< R_j_k])
	sum[i=1..N][j=1..M](A_i_j_k) = 0 OR sum[i=1..N][j=1..M](A_i_j_k) = R_k (either the project has enough people to commence or not done at all)
	sum[i=1..N][j=1..M](A_i_j_k) = 0 => RST_k = -1 (if project isn't commenced then there's no real startdate)
	sum[i=1..N][j=1..M](A_i_j_k) = 0 => RST_k = max[i=1..N](T_i_k)
	k>1: T_i_k = max[l=1..k-1,j=1..K](A_i_j_k * (RST_k + D_k))
	
	
Score function:
	Score = sum[k=1..M]([RST_k >= 0] * (S_k + max(0, RST_k + D_k - B_k))) -> max
	

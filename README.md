Vars:
	Person i (i=1..N):
		L_i_j >= 0, skill j of a person i (j=1..K)
	
	Project k (k = 1..M):
		D_k, Nb days to complete
		S_k, base score
		B_k, best before day
		R_k, NbRoles
		R_j_k>=0, requirement skill level for each role
		RST_k, real start date (-1 if the project isn't commences)
		
	Assignments:
		A_i_j_k, Boolean person i assigned to project k
		T_i_k, the date when a person i can start project i, T_i_1 = 0
		
Constraints:
	sum[l=1..K](A_i_j_k) =< 1 (the person cannot be assigned to handle more than one task in a project)
	A_i_j_k = 1 => (L_i_j >= R_j_k)
	sum[i=1..N][j=1..M](A_i_j_k) = 0 OR sum[i=1..N][j=1..M](A_i_j_k) = R_k (either the project has enough people to commence or not done at all)
	sum[i=1..N][j=1..M](A_i_j_k) = 0 => RST_k = -1 (if project isn't commenced then there's no real startdate)
	sum[i=1..N][j=1..M](A_i_j_k) = 0 => RST_k = max[i=1..N](T_i_k)
	i>1: T_i_k = ??????
	
Score function:
	Score = sum[k=1..M]([RST_k >= 0] * (S_k + max(0, RST_k + D_k - B_k)))
	

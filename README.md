# Find Frequent ItemSet With Multiple Item Support

This project is a Data Mining project wherein Association Rules between 2 objects such as bread, butter can be inferred. If a person buys {bread, butter} regularly then we can create an association rule like "bread -> butter". We have given a set of transactions as input and tried to infer the association rules between those objects using "Minimum Support Apriori Algorithm".

Each Item has it's own Minimum Item Support, so we have to take into consideration the difference between those items in an itemset, etc.

###The Minimum Support Apriori Algorithm.
```
Algorithm MS-Apriori(T, MS, SDC) // MS stores all MIS values
1 M = sort(I, MS); // according to MIS(i)’s stored in MS
2 L = init-pass(M, T); // make the first pass over T
3 F1 = {{l} | l belongs to L, l.count/n >= MIS(l)}; // n is the size of T
4 for (k = 2; Fk-1 != NULL; k++) do
5   if k = 2 then
6     Ck = level2-candidate-gen(L, SDC) // k = 2
7   else Ck = MScandidate-gen(Fk-1, SDC)
8   endif;
9   for each transaction t belongs to T do
10    for each candidate c belongs to Ck do
11      if c is contained in t then // c is a subset of t
12        c.count++
13      if c – {c[1]} is contained in t then // c without the first item
14        (c – {c[1]}).count++
15    endfor
16  endfor
17  Fk = {c bleongs to Ck | c.count/n >= MIS(c[1])}
18 endfor
19 return F = Union of all Fk;
```

###Level-2 Candidate Generation.
```
Function level2-candidate-gen(L, SDC)
1 C2 is NULL // initialize the set of candidates
2 for each item l in L in the same order do
3   if l.count/n >= MIS(l) then
4     for each item h in L that is after l do
5       if h.count/n >= MIS(l) and |sup(h) - sup(l)| ≤ SDC then
6         C2 belobgs to C2 UNION {{l, h}}; // insert the candidate {l, h} into C2
```

###Minimum Support Candidate Generation.
```
Function MScandidate-gen(Fk-1, SDC)
1 Ck is NULL // initialize the set of candidates
2 forall f1, f2 belongs to Fk // find all pairs of frequent itemsets
3   with f1 = {i1, … , ik-2, ik-1} // that differ only in the last item
4   and f2 = {i1, … , ik-2, i’k-1}
5   and ik-1 < i’k-1 and |sup(ik-1) - sup(i’k-1)| ≤ SDC do
6     c = {i1, …, ik-1, i’k-1}; // join the two itemsets f1 and f2
7     Ck = Ck UNION {c}; // insert the candidate itemset c into Ck
8     for each (k-1)-subset s of c do
9       if (c[1] belongs to s) or (MIS(c[2]) = MIS(c[1])) then
10        if (s is NOT IN Fk-1) then
11          delete c from Ck; // delete c from the set of candidates
12      endfor
13 endfor
14 return Ck; // return the generated candidates
```

###Example

```

Given the following seven transactions,
{Beef, Bread}
{Bread, Clothes}
{Bread, Clothes, Milk}
{Cheese, Boots}
{Beef, Bread, Cheese, Shoes}
{Beef, Bread, Cheese, Milk}
{Bread, Milk, Clothes}
and MIS(Milk) = 50%, MIS(Bread) = 70%, and 25% for all other items.


Again, the support difference constraint is not used. The following frequent
itemsets are produced:


F1 = {{Beef}, {Cheese}, {Clothes}, {Bread}}
F2 = {{Beef, Cheese}, {Beef, Bread}, {Cheese, Bread}
{Clothes, Bread}, {Clothes, Milk}}
F3 = {{Beef, Cheese, Bread}, {Clothes, Milk, Bread}}.

```

###References: - Exploring Hyperlinks, Contents and Usage Data by Bing Liu.
https://en.wikipedia.org/wiki/Apriori_algorithm


Please get back to us if you have any doubts in the implementation.

###Name: - Pavan Kumar Kothagorla
###Email: - pkotha6@uic.edu

###Name: - Sandeep Kumar A.
###Email: - sanilk2@uic.edu

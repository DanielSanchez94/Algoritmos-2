import Data.Maybe

-- Genera sublistas de una lista
sublist :: Eq a => [a] -> [[a]]
sublist [] = [[]]
sublist (x:xs) = [(x:ys) | ys<-sublist xs] ++ sublist xs

-- De una lista de listas elimina las listas que no son funcionales y totales
-- lista de estados - alfabeto - lista de deltas - lista de deltas filtrada
repOkList :: [Int] -> [Char] -> [[((Int,Char),Int)]] -> [[((Int,Char),Int)]]
repOkList ys zs [x] | functional x && total [(s,c) | s<-ys, c<-zs] x = [x]
                    | otherwise = []
repOkList ys zs (x:xs)  | functional x && total [(s,c) | s<-ys, c<-zs] x = x : repOkList ys zs xs
                        | otherwise = repOkList ys zs xs

-- Verifica si una lista delta es funcional
-- un delta - true si es funcional
functional :: [((Int,Char),Int)] -> Bool
functional [] = True
functional [x] = True
functional (x:y:xs)  |fst x == fst y && snd x /= snd y = False
                |fst x == fst y && snd x == snd y = False
                |otherwise = True && functional (y:xs)

-- Verifica si una lista delta es total
-- un delta - true si es total
total ::[(Int,Char)] -> [((Int,Char),Int)] -> Bool
total [] _ = True
total (x:xs) ys = elem x (reduc ys) && total xs ys
              where reduc ts = [fst t | t <- ts]

--Elimina los elementos repetido de una lista
removeDups :: Eq a => [a] -> [a]
removeDups [] = []
removeDups (x:xs)
          | elem x xs = (removeDups xs)
          | otherwise  = x:(removeDups xs)

-- Devuelve true si la interseccion entre dos listas de palabras es distinta de vacio, False en caso contrario
intersec :: [String] -> [String] -> Bool
intersec xs [] = False
intersec xs (y:ys) = elem y xs || intersec xs ys

-- |Data type representing a Deterministic Finite Automaton, parametrized by a type of states.
type DFA st = ([st], [Char], st->Char->st, st, st->Bool)

-- | Function f_M: Sigma* -> {True,False} defined by DFA M
-- Uses auxiliary function delta*: Q x Sigma* -> Q
evalDFA :: DFA st -> String -> Bool
evalDFA (qs, sigma, delta, s, inF) w =
  inF (deltaStar s w)
  where deltaStar q [] = q
        deltaStar q (a:w) = deltaStar (delta q a) w

--Crea una funcion a partir de una lista de tuplas
funFromTuples :: Eq a =>[(a,b)] -> a -> b
funFromTuples ts x = fromJust (lookup x ts)

-- Evalua si un DFA acepta un grupo de palabras y no acepta otro grupo
-- automata - palabras pos - palabras neg - true si funciona
evalString :: DFA Int -> [String] -> [String] ->Bool
evalString dfa [] [] = True
evalString dfa (x:xs) [] = evalDFA dfa x && evalString dfa xs []
evalString dfa [] (y:ys) = not (evalDFA dfa y) && evalString dfa [] ys
evalString dfa (x:xs) (y:ys) = (evalDFA dfa x) && (not (evalDFA dfa y)) && evalString dfa xs ys

-- Genera una lista de automatas variando el delta y el conjunto de estados finales
-- lista de estados - alfabeto - lista de lista de tuplas (a,b) donde a es una tupla (estado, caracter) y b un estado - lista de automatas
genDFA :: [Int] -> [Char] -> [[((Int,Char),Int)]] -> [DFA Int]
genDFA xs ys zs = [(xs, ys, (\s c -> funFromTuples d (s,c)), 0, (\s -> elem s z)) | z<-sublist xs, d<-zs]

-- Devuelve una lista de listas, es decir, una lista de deltas
-- lista de estados - alfabeto - lista de lista de tuplas (a,b) donde a es una tupla (estado, caracter) y b un estado
generateDelta :: [Int] -> [Char] -> [[((Int,Char),Int)]]
generateDelta xs ys = removeDups (repOkList xs ys (init (sublist [((x,y),z) | x <- xs, y <- ys, z <- xs])))

-- Genera automatas a partir de 1 estado a k estados y se queda con el primero que acepte pos y rechase neg
-- limite de estados k - contador - alfabeto - lista de automatas - palabras pos - palabras neg - automata
auxFunction :: Int -> Int -> [Char] -> [DFA Int] -> [String] -> [String] -> DFA Int
auxFunction k n alfab [] pos neg | n<=k = auxFunction k (n+1) alfab (genDFA [x | x <- [0..n-1]] alfab (generateDelta [x | x <- [0..n-1]] alfab)) pos neg
                                 | otherwise = error "No es posible formar un automata"
auxFunction k n alfab (x:xs) pos neg | evalString x pos neg = x
                                     | otherwise = auxFunction k n alfab xs pos neg

-- Funcion principal, utilizada para inicializar a la funcion auxFunction
-- limite de estados k - alfabeto - palabras pos - palabras neg - (lista de estados,alfbeto, lista delta, estado inicial)
mainFunction :: Int -> [Char] -> [String] -> [String] -> ([Int],[Char],[((Int, Char),Int)],Int,[Int])
mainFunction 0 _ _ _ = error "No se puede formar DFA con 0 estados"
mainFunction k alfab pos neg | intersec pos neg = error "No es posible encontrar automata que reconozca y no reconozca una palabra"
                             | otherwise = printDFA (auxFunction k 2 alfab (genDFA [0] alfab (generateDelta [0] alfab)) pos neg)

-- Imprime un automata con la delta en formato de lista (Aun no se muestran los estados finales)
-- automata - (lista de estados,alfbeto, lista delta, estado inicial)
printDFA :: DFA Int -> ([Int],[Char],[((Int, Char),Int)],Int,[Int])
printDFA (qs,sigma,delta,s,inF) = (qs,sigma,getDelta qs sigma delta,s, getFinals qs inF)

-- Funcion para recuperar la lista delta desde funFromTuples
-- lista de estados - alfabeto - funcion delta - delta en forma de lista
getDelta :: [Int] -> [Char] -> (Int->Char->Int) -> [((Int,Char),Int)]
getDelta qs sigma delta = [((q,c),delta q c) | q<-qs, c<-sigma ]

--Funcion para recuperar el conjunto de estados finales
getFinals :: [Int] -> (Int -> Bool) -> [Int]
getFinals qs inF = [q | q<-qs, inF q]

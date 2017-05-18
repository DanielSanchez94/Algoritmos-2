
--                              MAIL 1
-- Queremos construir un autómata que reconozca un lenguaje (conjunto de cadenas),
-- pero no tenemos formalizado el lenguaje, ni tenemos en principio idea de cómo
-- construirlo. Entonces lo que haremos es darle a una aplicación ejemplos
-- de qué cadenas quisiéramos que reconozca y qué cadenas no quisiéramos que
-- reconozca, y el sistema tratará de construir un autómata de a lo sumo k estados
-- (el k lo pasa el usuario) que reconozca las cadenas que queremos que reconozca,
-- y no reconozca las que no queremos que reconozca, o nos dirá que no se puede
-- construir un autómata de esas características con ese número de estados.
-- Por ejemplo, si queremos un autómata que nos reconozca las cadenas compuestas
-- por sólo la a, sobre el alfabeto {a, b, c}, podríamos pasarle como ejemplos
-- positivos y negativos, respectivamente, los siguientes:

-- “”, “aa”, “aaa”, “aaaaaaa”
-- “b”, “c”, “bc”, “bbb”, “ccc”
-- y el sistema tratará de construir (por fuerza bruta) el autómata. Podría,
-- para k=2, darnos por ejemplo el siguiente autómata

-- Estados: S0 (inicial y final), S1
-- delta (S0, a) = S0
-- delta (S0,b) = S1
-- delta (S0,c) = S1
-- delta (S1, x) = S1
-- O sea, la salida esperada de la aplicación es un autómata que reconozca las
-- cadenas positivas, no reconozca las negativas, o simplemente que no pudo
-- construir uno (en caso de no existir tal autómata de hasta k estados).
-- Respecto a las condiciones para los conjuntos de cadenas, deberían ser
-- disjuntas (si una misma cadena está entre las positivas y las negativas
-- entonces no podemos construir ningún autómata que la reconozca y al mismo
-- tiempo no la reconozca), pero no hace falta suponerlo, ni tratar especialmente
-- ese caso.

--                                 MAIL 2
-- Con respecto al primero de los ejercicios del TP1, seguramente necesitarán,
-- dado un conjunto de estados, generar todas las funciones de transición
-- (las "delta") posibles sobre ese conjunto de estados. Esto no es obvio de
-- conseguir, dado que, al menos con la definición de autómatas que les pasé,
-- un DFA espera que esta delta sea efectivamente una función.

-- Una posibilidad que les recomiendo consiste en, dado un conjunto de estados S
-- y alfabeto Sigma, generar todas las posibles relaciones ternarias
-- de S x Sigma x S. Esto es fácil, porque son todos los posibles subconjuntos
-- de S x Sigma x S (es decir, de tuplas (s, c, s'), con s y s' estados y c símbolo
-- del alfabeto). Sobre ese conjunto, sólo tienen que filtrar las que sean
-- funcionales (es decir, que no mapeen a un mismo símbolo desde un mismo estado a
-- dos estados destino diferentes) y totales (es decir, que para cada símbolo del
-- alfabeto y estado, indiquen a qué estado destino pasar).

-- Si consiguen resolver este problema, tendrán una familia de conjuntos de tuplas,
-- cada una de las cuales "representa" una función. Para "convertir" un conjunto de
-- tuplas en una función, pueden usar lo siguiente:

import Data.Maybe
-- |Data type representing a Deterministic Finite Automaton, parametrized by a type of states.
type DFA st = ([st], [Char], st->Char->st, st, st->Bool)

-- | Function f_M: Sigma* -> {True,False} defined by DFA M
-- Uses auxiliary function delta*: Q x Sigma* -> Q
evalDFA :: DFA st -> String -> Bool
evalDFA (qs, sigma, delta, s, inF) w =
  inF (deltaStar s w)
  where deltaStar q [] = q
        deltaStar q (a:w) = deltaStar (delta q a) w

sampleDFA :: DFA Int
sampleDFA = ([0,1], ['a','b','c'], trans, 0, (\s->s==1))
            where trans s c | s==0 && c=='a' = 1
                            | otherwise = s

--Crea una funcion a partir de una lista de tuplas
funFromTuples :: Eq a =>[(a,b)] -> a -> b
funFromTuples ts x = fromJust (lookup x ts)

-- Genera sublistas de una lista
sublist :: Eq a => [a] -> [[a]]
sublist [] = [[]]
sublist (x:xs) = [(x:ys) | ys<-sublist xs] ++ sublist xs

-- Producto cartesiano entre 1 lista de estados y 1 lista de caracteres
prodTwoList :: [st] -> [Char] -> [(st, Char)]
prodTwoList xs ys = [(x,y) | x <- xs, y <- ys]

--Producto cartesiano entre 1 lista de tuplas y 1 lista de estados
prodTupleList :: [(st, Char)] -> [st] -> [((st,Char),st)]
prodTupleList xs zs = [((fst x, snd y),z) | x <- xs, y <- xs, z <- zs]

-- Genera todos los posibles estados de la funcion de transicion delta
generateDelta :: Eq st => [st] -> [Char] -> [[((st,Char),st)]]
generateDelta xs ys = removeDups (repOkList (sublist (prodTupleList (prodTwoList xs ys) xs)))
--generateDelta xs ys = sublist (prodTupleList (prodTwoList xs ys) xs)

--Filtra todas las delta
repOk :: Eq a => Eq b => [(a,b)] -> Bool
repOk [] = True
repOk [x] = True
repOk (x:y:xs)  |fst x == fst y && snd x /= snd y = False
                      |fst x == fst y && snd x == snd y = False
                      |otherwise = True && repOk (y:xs)
-- De una lista de listas elimina las listas que no cumplen con repOk
repOkList :: Eq a => Eq b => [[(a,b)]] -> [[(a,b)]]
repOkList [[]] = [[]]
repOkList (x:xs)  | repOk x = x : repOkList xs
                  | otherwise = repOkList xs

--Elimina los elementos repetido de una lista
removeDups :: Eq a => [a] -> [a]
removeDups [] = []
removeDups (x:xs)
          | elem x xs = (removeDups xs)
          | otherwise  = x:(removeDups xs)

-- Generar un lista de todas las posibles cantidades de estados menores o iguales a k
generateEst :: Int -> [[Int]]
generateEst n = init (sublist ([0..n]))

-- Evalua si un DFA hacepta un grupo de palabras y no acepta otro grupo
evalString :: DFA Int -> [String] -> [String] ->Bool
evalString dfa [] [] = True
evalString dfa [] [y] = not (evalDFA dfa y)
evalString dfa [x] [] = evalDFA dfa x
evalString dfa (x:xs) (y:ys) = (evalDFA dfa x) && (not (evalDFA dfa y)) && evalString dfa xs ys


-- Genera distintos DFA de acuerdo a la lista de lista de deltas y prueba si alguno funciona
testDeltaList :: [st] -> [Char] -> [[((st,Char),st)]]-> [String] -> [String] -> [st] -> DFA Int
testDeltaList _ _ [] _ _ _ = error "DELTA VACIO"
testDeltaList sts alf (x:xs) pos neg fin  | evalString (sts,alf,funFromTuples,0,\s-> s elem fin) pos neg = (sts,alf,delta,0,fin)
                                          | otherwise = testDeltaList sts alf xs pos neg fin
                                          where delta s c = funFromTuples x (s,c)

testFinalStateList :: [st] -> [Char] -> [String] -> [String] -> [[st]] -> DFA Int
testFinalStateList _ _ _ _ [] = error "CONJUNTO DE ESTADOS FINALES VACIO"
testFinalStateList sts alf pos neg (x:xs) | (testDeltaList st alf (generateDelta st alf) pos neg x) \= error = testDeltaList sts alf pos neg x
                                    | otherwise = testFinalStateList sts alf pos neg xs

testStateList :: [[st]] -> [Char] -> [String] -> [String] -> DFA Int
testStateList [[]] _ _ _ = error "CONJUNTOS DE ESTADOS VACIO"
testStateList (x:xs) alf pos neg | (testFinalStateList sts alf pos neg (sublist x)) \= error = testFinalStateList sts alf pos neg (sublist sts)
                                | otherwise = testStateList xs alf pos neg

generateDFA :: Int -> Int -> [Char] -> [String] -> [String] -> DFA Int
--generateDFA k k _ _ _ = testStateList (generateEst n) alf pos neg
generateDFA k n alf pos neg | n<=k && (testStateList (generateEst n) alf pos neg \= error) = testStateList (generateEst n) alf pos neg
                            | n<k && (testStateList (generateEst n) alf pos neg == error) = generateDFA k (n+1) alf pos neg
                            | otherwise = error "NO ES POSIBLE GENERAR DFA"

principalMethod :: Int -> [Char] -> [String] -> [String] -> DFA Int
principalMethod 0 _ _ _ = error "IMPOSIBLE GENERAR DFA CON CERO ESTADOS"
--principalMethod k alf pos neg | intersecPosNeg pos neg = error "INTERSECCION ENTRE POS Y NEG NO VACIA"
--                              | otherwise = generateDFA k 1 alf pos neg
principalMethod k alf pos neg = generateDFA k 1 alf pos neg

--intersecPosNeg :: [String] -> [String] -> Bool
--intersecPosNeg xs [] = True
--intersecPosNeg xs (y:ys) = not (y elem xs) && intersecPosNeg xs ys









--

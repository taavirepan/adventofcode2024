import z3

rA = x = z3.BitVec('x', 48)
# rA = 107416870455451
rB = 0
rC = 0

program = [2,4,1,5,7,5,1,6,4,2,5,5,0,3,3,0]

def takesCombo(func):
    def wrapper(literal):
        combo = {4: rA, 5: rB, 6: rC}.get(literal, literal)
        return func(combo)
    return wrapper

@takesCombo
def adv(arg):
    global rA, rB, rC
    rA = rA >> arg

@takesCombo
def cdv(arg):
    global rA, rB, rC
    rC = rA >> arg

def bxl(arg):
    global rA, rB, rC
    rB = rB ^ arg

def bxc(_):
    global rA, rB, rC
    rB = rB ^ rC

@takesCombo
def bst(arg):
    global rA, rB, rC
    rB = arg & 7

@takesCombo
def out(arg):
    global rA, rB, rC, eqs
    y = target.pop(0)
    print(f"solving {arg & 7} == {y}")
    eqs.append(arg & 7 == y)

@takesCombo
def jnz(arg):
    global rA, rB, rC
    if len(target) == 0:
        eqs.append(rA == 0)

opcodes = {
    0: adv,
    1: bxl,
    2: bst,
    3: jnz,
    4: bxc,
    5: out,
    # 6: bdv
    7: cdv,
}

eqs = []
target = list(program)
while target:
    for opcode, literal in zip(program[::2], program[1::2]):
        opcodes[opcode](literal)

print(eqs)
eqs.append(x < 107416870455451) # manually forcing to find the smallest one
z3.solve(eqs)
# Générateur de mot de passe

## Description 

Ce projet est un générateur de mot de passe aléatoire.
Il permet de sélectionner au moment de l'instanciation un niveau de sécurité,
ou de choisir manuellement le nombre de chaque type de caractères (alpha-numérique / spéciaux)

## Paramétrage

Différentes parties du programme peuvent être modifiées / paramétrées.

### 1. Listes des caractères

Il existe trois listes de caractères dans ce programme : 
- les lettres (toutes les lettres de **a à z** ainsi que leurs copies en majuscules)
- les chiffres (tous les caractères numérique de **0 à 9**)
- les caractères spéciaux (caractères compris dans les caractères suivants : **@&()§:=+-_%$\*!?,**)

Ces listes sont définies comme étant des listes de `char` privée stocké en tant qu'argument du générateur.

```java
private final static char[] letter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
```

```java
private final static char[] numericChar = "0123456789".toCharArray();
```

```java
private final static char[] specialChar = "@&()§:=+-_%$*!?,".toCharArray();
```

Pour ajouter / supprimer un caractère de cette liste, il suffit donc de modifier les chaines de caractères directement dans la classe du générateur.

### 2. Taille minimum

La taille minimum du mot de passe est simplement représenté par une constante dans la classe dugénérateur.

```java
private final int MIN_LENGTH = 6;
```

Pour la modifier, il suffit donc de modifier cette constante.

### 3. Niveaux de protection

Il est possible d'instancier un générateur de mot de passe en lui passant en paramètre un enum de type `PasswordGenerator.PasswordProtection`.

Cet enum peut prendre par défaut 4 valeurs :
- `LOW` : longueur égale à la taille minimale du mot de passe
- `MEDIUM` : comporte au minimum 2 types de caractères
- `STRONG` : possède tous les types de caractères, et mesure au minimum 12 carcatères
- `CRAZY` : réuni toutes les conditions précédentes et fait au minimum 20 caractères de longueur

Vous n'avez pas besoin de déterminer ce niveau de sécurité à la main. Il est calculé automatiquement par le générateur à chaque changement.

Pour ajouter un niveau de difficulté, il faut donc dans un premier temps l'ajouter à l'enum à l'intérieur du générateur :

```java
public enum PasswordProtection {
    LOW, // MINIMUM OF LENGTH
    MEDIUM, // MINIMUM TWO TYPE OF CHARACTER
    STRONG, // MINIMUM ALL CHARACTER + LENGTH OF 12
    CRAZY // WOW MEN, MINIMUM ALL CHARACTER + LENGTH OF 20
}
```

Puis modifier la méthode `setLevelOfProtection()` en y ajoutant vos conditions

```java
private void setLevelOfProtection() {
    if (this.numberOfNumericChar > 0 && this.numberOfLetter > 0 && this.numberOfSpecialChar > 0) {
        if (getLength() >= 20) {
            this.levelOfProtection = PasswordProtection.CRAZY;
        } else {
            this.levelOfProtection = PasswordProtection.STRONG;
        }
    } else if (
            (this.numberOfNumericChar > 0 && this.numberOfLetter > 0) ||
                    (this.numberOfSpecialChar > 0 && this.numberOfLetter > 0) ||
                    (this.numberOfNumericChar > 0 && this.numberOfSpecialChar > 0)
    ) {
        this.levelOfProtection = PasswordProtection.MEDIUM;
    } else {
        this.levelOfProtection = PasswordProtection.LOW;
    }
}
```

## Utilisation

### a. Sans arguments

```java
PasswordGenerator passwordGen = new PasswordGenerator();
```

Avec ce type de construction, passwordGen sera définit avec une protection à `PasswordProtection.LOW`.
Il sera donc composé de 6 lettres

```java
PasswordGenerator passwordGen = new PasswordGenerator();
System.out.println(passwordGen.generatePassword());
```

```bash
> "aGjsiK"
```

### b. Avec le niveau de robustesse

```java
PasswordGenerator passwordGen = new PasswordGenerator(PasswordProtection.STRONG);
```

Avec ce type de construction, passwordGen sera définit avec une protection donnée en argument. Il aura donc la configuration de base de cette protection.

```java
PasswordGenerator passwordGen = new PasswordGenerator(PasswordProtection.STRONG);
System.out.println(passwordGen.generatePassword());
```

```bash
> "0h7Z0+p&M0tu7Q1"
```

### c. Avec un argument

```java
PasswordGenerator passwordGen = new PasswordGenerator(7);
```

Ici le paramètre donné est le nombre de caractère de type `letters`.

```java
PasswordGenerator passwordGen = new PasswordGenerator(7);
System.out.println(passwordGen.generatePassword());
```

```bash
> "aGhjOqK"
```

#### Exception

Si le nombre donné est inférieur à la taille minimum, une exception est levée

```java
PasswordGenerator passwordGen = new PasswordGenerator(4);
System.out.println(passwordGen.generatePassword());
```

```bash
> Exception : Minimum password size is not reached
```

### d. Avec deux arguments

```java
PasswordGenerator passwordGen = new PasswordGenerator(7, 6);
```

Ici le premier paramètre donné est le nombre de caractère de type `letters`.
Le second est le nombre de chiffre présent dans le mot de passe.

```java
PasswordGenerator passwordGen = new PasswordGenerator(7, 6);
System.out.println(passwordGen.generatePassword());
```

```bash
> "37aG6hjO8q8K9"
```

### e. Avec trois arguments

```java
PasswordGenerator passwordGen = new PasswordGenerator(7, 6, 4);
```

Ici le premier paramètre donné est le nombre de caractère de type `letters`.
Le second est le nombre de chiffre présent dans le mot de passe.
Le troisième est le nombre de caractères spéciaux présent dans le mot de passe.

```java
PasswordGenerator passwordGen = new PasswordGenerator(7, 6, 4);
System.out.println(passwordGen.generatePassword());
```

```bash
> "&37(aG6h*jO8$q8K9"
```
